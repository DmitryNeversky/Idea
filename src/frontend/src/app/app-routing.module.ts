import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {IdeasResolver} from "./shared/resolvers/ideas.resolver";
import {IdeasComponent} from "./ideas/ideas.component";
import {CreateIdeaComponent} from "./create-idea/create-idea.component";
import {IdeaFillComponent} from "./idea-fill/idea-fill.component";
import {IdeaResolver} from "./shared/resolvers/idea.resolver";
import {HomeComponent} from "./home/home.component";
import {TagResolver} from "./shared/resolvers/tag.resolver";
import {UpdateIdeaComponent} from "./update-idea/update-idea.component";
import {AuthGuard} from "./auth.guard";
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {UserComponent} from "./user/user.component";

const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        data: { animation: 'home' },
        canActivate: [AuthGuard],
    },
    {
        path: 'ideas',
        component: IdeasComponent,
        data: { animation: 'ideas' },
        canActivate: [AuthGuard],
        resolve: {
            ideas: IdeasResolver
        },
    },
    {
        path: 'ideas/id/:id',
        component: IdeaFillComponent,
        data: { animation: 'idea' },
        canActivate: [AuthGuard],
        resolve: {
            idea: IdeaResolver
        }
    },
    {
        path: 'ideas/create',
        component: CreateIdeaComponent,
        data: { animation: 'idea-create' },
        canActivate: [AuthGuard],
        resolve: {
            tags: TagResolver
        }
    },
    {
        path: 'ideas/update/:id',
        component: UpdateIdeaComponent,
        data: { animation: 'update-idea' },
        canActivate: [AuthGuard],
        resolve: {
            idea: IdeaResolver
        }
    },
    {
        path: 'user',
        component: UserComponent,
        data: { animation: 'user' }
    },
    {
        path: 'auth',
        component: LoginComponent,
        data: { animation: 'auth' }
    },
    {
        path: 'registration',
        component: RegistrationComponent,
        data: { animation: 'registration' }
    },
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}